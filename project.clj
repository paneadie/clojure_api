(defproject input-box "0.1.0-SNAPSHOT"
  :description "Stupidia"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [ring/ring-core "1.2.1"]
                 [ring/ring-devel "1.4.0"]
                 [ring/ring-jetty-adapter "1.2.1"]
                 [compojure "1.4.0"]
                 [hiccup "1.0.5"]
                 [clj-http "2.0.0"]
                  ]
  :dev-dependencies
       [[lein-run "1.0.0-SNAPSHOT"]]
  :main input-box.core)
