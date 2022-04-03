(ns tdd.logic.calc-test
  (:require ["@jest/globals" :refer [test expect]]
            [tdd.logic.calc :as calc]))

(test
 "returns 0 for empty string"
 (fn []
   (-> (expect (calc/add ""))
       (.toBe 0))))

(test
 "return 1 for string 1"
 (fn []
   (-> (expect (calc/add "1"))
       (.toBe 1))))

(test
 "return 2 for string 2"
 (fn []
   (-> (expect (calc/add "2"))
       (.toBe 2))))

(test
 "performs simple addition"
 (fn []
   (-> (expect (calc/add "1,2"))
       (.toBe 3))))

(test
 "throws on invalid input"
 (fn []
   (-> (expect (fn [] (calc/add "a")))
       (.toThrow #"Invalid input"))))

(test
 "throws on bare comma"
 (fn []
   (-> (expect (fn [] (calc/add ",")))
       (.toThrow #"Invalid input"))))

(test
 "throws on stray comma"
 (fn []
   (-> (expect (fn [] (calc/add ",1")))
       (.toThrow #"Invalid input"))))
