(ns heroku.models.movies
  (require [clojure.data.json :as json]
           [org.httpkit.client :as http])
  (import [org.jsoup Jsoup]))

#_(def queries {:select [""]
              :insert [""]})

#_(defn parse-json-field
  [v]
  (-> v
      first
      :movies
      (.toString)
      json/read-str))

(def user-agent
  "user-agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/67.0.3396.79 Safari/537.36")

(def host "https://filmarks.com")

;;; 30 minutes
(def expire-in-ms (* 30 60 1000))

(def movies (atom nil))

(defn expired? [{:keys [timestamp]}]
  (> (System/currentTimeMillis) (+ timestamp expire-in-ms)))

(defn fetch-html []
  (http/get host
            {:user-agent user-agent}))

(defn extract-movies [response]
  (let [doc (Jsoup/parse (:body @response))]
    (map #(-> %
              (.getElementsByTag "a")
              (.attr "href"))
         (.getElementsByClass doc "c-movie-item"))))

(defn set-movies! []
  (let [new-one (extract-movies (fetch-html))]
    (reset! movies {:movies new-one
                   :count (count new-one)
                   :timestamp (System/currentTimeMillis)})))

(defn random-movie [{:keys [count movies]}]
  (str host (nth movies (rand-int count))))

(defn get-paths []
  (let [m @movies]
    (if (or (nil? m) (expired? m))
      (set-movies!)
      m)))



