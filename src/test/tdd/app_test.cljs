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
     (-> (expect (cmdline/last-output cmdline)) (.toBe "Uryyb\n")))))

(test
 "handles too few args"
 (fn []
   (let [cmdline (cmdline/create-null {:args []})
         app (x/create {:cmdline cmdline})]
     (x/run app)
     (-> (expect (cmdline/last-output cmdline)) (.toBe "Too few args\n")))))

(test
 "handles too many args"
 (fn []
   (let [cmdline (cmdline/create-null {:args ["a" "b"]})
         app (x/create {:cmdline cmdline})]
     (x/run app)
     (-> (expect (cmdline/last-output cmdline)) (.toBe "Too many args\n")))))
