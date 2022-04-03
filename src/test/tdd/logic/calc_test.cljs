(ns tdd.logic.calc-test
  (:require ["@jest/globals" :refer [test expect]]
            [tdd.logic.calc :as calc]))

(test
 "returns 0 for empty string"
 (fn []
   (-> (expect (calc/add ""))
       (.toBe 0))))
