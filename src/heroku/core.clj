(ns heroku.core
  (:require [org.httpkit.server :as http]
            [heroku.models.movies :as movies]
            [clojure.data.json :as json]
            [compojure.core :refer :all]
            [ring.middleware.json :refer [wrap-json-response]]
            [ring.util.response :refer [response]])
  (:gen-class))

(defroutes all-routes
           (GET "/" []
                (let [v (movies/get-paths)]
                  (response {:url (movies/random-movie v)}))))

(def app
  (wrap-json-response all-routes))

(defonce server (atom nil))

(defn stop-server []
  (when-not (nil? @server)
    ;; graceful shutdown: wait 100ms for existing requests to be finished
    ;; :timeout is optional, when no timeout, stop immediately
    (@server :timeout 100)
    (reset! server nil)))

(defn -main
  [& args]
  (println "server started...")
  (reset! server (http/run-server #'app {:port (if-let [p (System/getenv "PORT")]
                                                 (Integer/parseInt p)
                                                 8080)})))
