(defproject ring-app "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [ring "1.4.0"]
                 [metosin/ring-http-response "0.6.5"]
                 [ring-middleware-format "0.7.0"]
                 [compojure "1.4.0"]]
  :main ring-app.core)