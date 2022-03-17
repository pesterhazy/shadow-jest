(ns tdd.app-test
  (:require [tdd.app :as x]
            [tdd.infra.cmdline :as cmdline]
            [jest.matchers]))

(js/test
 "runs the app"
 (fn []
   (let [cmdline (cmdline/create-null {:args ["Hello"]})
         app (x/create {:cmdline cmdline})]
     (x/run app)
     (-> (js/expect (cmdline/last-output cmdline)) (.toBe "Uryyb\n")))))
