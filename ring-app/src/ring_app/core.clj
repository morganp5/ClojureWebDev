(ns ring-app.core
  (:require [ring.adapter.jetty :as jetty]
            [ring.util.http-response :as response]
            [ring.middleware.reload :refer [wrap-reload]]
            [ring.middleware.format :refer [wrap-restful-format]]
            [compojure.core :as compojure]))



(defn response-handler [request]
  (response/ok
    (str "<html><body> your IP is: "
         (:remote-addr request)
         "</body></html>")))

#_(def handler
  (compojure/routes
    (compojure/GET "/" request response-handler)
    (compojure/GET "/:id" [id] (str "<p>the id is: " id "</p>" ))))

(compojure/defroutes handler
                     (compojure/GET "/" request response-handler)
                     (compojure/GET "/:id" [id] (str "<p>the id is: " id "</p>" ))
                     (compojure/POST "/json" [id] (response/ok {:result id}))
                     (GET "/foo" request (interpose ", " (keys request))))

(defn wrap-nocache [handler]
  (fn [request]
    (-> request
        handler
        (assoc-in [:headers "Pragma"] "no-cache"))))

(defn wrap-formats [handler]
  (wrap-restful-format
    handler
    {:formats [:json-kw :transit-json :transit-msgpack]}))

(defn -main []
  (jetty/run-jetty
    (-> #'handler wrap-nocache wrap-reload wrap-formats)
    {:port 3000
     :join? false}))