(ns tdd.app-test
  (:require ["@jest/globals" :refer [test expect]]
            [tdd.app :as x]
            [tdd.infra.cmdline :as cmdline]))

(test
 "runs the app"
 (fn []
   (let [cmdline (cmdline/create-null {:args ["Hello"]})
         app (x/create {:cmdline cmdline})]
     (x/run app)
     (-> (expect) .pass))))
