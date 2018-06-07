(defproject heroku "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/data.json "0.2.6"]
                 [http-kit "2.2.0"]
                 [org.jsoup/jsoup "1.8.3"]
                 [compojure "1.6.1"]
                 [ring/ring-json "0.4.0"]]
  :main ^:skip-aot heroku.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
