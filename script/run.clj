(use 'ring.adapter.jetty)
(require 'input-box.core)

(run-jetty #'input-box.core/app {:port 8080})
