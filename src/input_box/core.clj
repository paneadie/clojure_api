(ns input-box.core
  (:require [ring.adapter.jetty :as jetty]
            [compojure.core :refer :all]
            [compojure.route :as route]
            [clj-http.client :as client]
            [hiccup.core :as hiccup]
            )
  (use hiccup.core)
  (use ring.middleware.reload)
  (use ring.middleware.stacktrace)
  (use ring.middleware.params))


(def doctype
  {:html4
   (str "<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01//EN\" "
        "\"http://www.w3.org/TR/html4/strict.dtd\">\n")
   :xhtml-strict
   (str "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\" "
        "\"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">\n")
   :xhtml-transitional
   (str "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" "
        "\"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n")
   :html5
   "<!DOCTYPE html>\n"})

(defn xhtml-tag
  "Create an XHTML element for the specified language."
  [lang & contents]
  [:html {:xmlns     "http://www.w3.org/1999/xhtml"
          "xml:lang" lang
          :lang      lang}
   contents])





(defn view-layout [& content]
  (html
    (doctype :html5)
    (xhtml-tag "en"
               [:head
                [:meta {:http-equiv "Content-type"
                        :content    "text/html; charset=utf-8"}]
                [:title "input-box"]]
               [:body content])))

(defn view-input [& [a b]]
  (view-layout
    [:h2 "Stupedia -  last year."]
    [:form {:method "post" :action "/"}
     (if (and a b)
       [:p "those are not both numbers!"])
     [:input {:type "text" :name "a" :value a}]
     [:input {:type "text" :name "b" :value b}]
     [:br]
     [:input.action {:type "submit" :value "Go stoogle"}]]))


(defn view-output [a b sum]
  (view-layout
    [:h2 "two numbers added"]
    [:p.math a " + " b " = " sum]
    [:a.action {:href "/"} "add more numbers"]))


(defn results-raw [a]
   (clojure.walk/keywordize-keys (client/get a  ))
  )

(defn findMyVal [myMap]
  (doseq [[k v] myMap] (if (map? v) (findMyVal v) (println v) ) ) )

(defn view-wiki [a b ]
  (view-layout
    ( findMyVal (results-raw a) ) )
    )

(defn OLD-view-wiki [a b ]
  (view-layout
   (get-in (results-raw a) [(keyword b)] (results-raw a) )
) )



(defroutes handler
           (GET "/" []
             (view-input))

           (POST "/" [a b]
                 (view-wiki a b)))



(def app
  (-> #'handler
      (wrap-reload '[input-box.core])
      (wrap-stacktrace)
      (wrap-params '[input-box.core])))


(defn -main []
  (jetty/run-jetty #'input-box.core/app {:port 8080}))




