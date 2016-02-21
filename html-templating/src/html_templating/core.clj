(ns html-templating.core
  (:require [selmer.parser :as selmer]
            [selmer.filters :as filters]))

(defn empty-filter-test
  [x]
  "Adds a filter to check if a sequence is empty"
  (filters/add-filter! :empty? empty?)
  (selmer/render "{% if files|empty? %}no files{% else %}files{% endif %}"
                 {:files []}))

(defn get-filter-content
  [x]
  (filters/add-filter! :foo
                       (fn [x] [:safe (.toUpperCase x)]))
  (selmer/render "{{x|foo}}" {:y "<div>I'm safe</div>"}))

(defn add-custom-tag
  []
  (selmer/add-tag!
    :image
    (fn [args context-map]
      (str "<img src=" (first args) "/>")))
  (selmer/render "{% image \"http://foo.com/logo.jpg\" %}" {}))

(defn overload-tag
  []
  (selmer/add-tag!
    :uppercase
    (fn [args context-map content]
      (.toUpperCase (get-in content [:uppercase :content])))
    :enduppercase)
  (selmer/render "{% uppercase %}foo {{bar}} baz{% enduppercase %}" {:bar "injected"}))

(defn render-hello-template
  [x]
  (selmer/render-file "hello.html" {:name x :items (range 10)}))


