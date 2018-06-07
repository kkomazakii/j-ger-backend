(ns heroku.core
  (:require [org.httpkit.server :as http])
  (:gen-class))

(defn app [req]
  {:status  200
   :headers {"Content-Type" "application/json"}
   :body    "{\"url\": \"https://filmarks.com/movies/71657\"}"})

(defonce server (atom nil))

(defn stop-server []
  (when-not (nil? @server)
    ;; graceful shutdown: wait 100ms for existing requests to be finished
    ;; :timeout is optional, when no timeout, stop immediately
    (@server :timeout 100)
    (reset! server nil)))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (reset! server (http/run-server #'app {:port (if-let [p (System/getenv "PORT")]
                                                 (Integer/parseInt p)
                                                 8080)})))
