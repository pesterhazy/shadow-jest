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

(js/test
 "handles too few args"
 (fn []
   (let [cmdline (cmdline/create-null {:args []})
         app (x/create {:cmdline cmdline})]
     (x/run app)
     (-> (js/expect (cmdline/last-output cmdline)) (.toBe "Too few args\n")))))

(js/test
 "handles too many args"
 (fn []
   (let [cmdline (cmdline/create-null {:args ["a" "b"]})
         app (x/create {:cmdline cmdline})]
     (x/run app)
     (-> (js/expect (cmdline/last-output cmdline)) (.toBe "Too many args\n")))))
