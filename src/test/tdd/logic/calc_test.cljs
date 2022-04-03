(ns tdd.logic.calc-test
  (:require ["@jest/globals" :refer [test expect describe]]
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
 "handle newlines"
 (fn []
   (-> (expect (calc/add "1\n2,3"))
       (.toBe 6))))

(describe
 "error handling"
 (fn []
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
    "throws on leading comma"
    (fn []
      (-> (expect (fn [] (calc/add ",1")))
          (.toThrow #"Invalid input"))))

   (test
    "throws on traling comma"
    (fn []
      (-> (expect (fn [] (calc/add "1,")))
          (.toThrow #"Invalid input"))))

   (test
    "throws on invalid input"
    (fn []
      (-> (expect (fn [] (calc/add "1a")))
          (.toThrow #"Invalid input"))))

   (test
    "throws on traling comma and newline"
    (fn []
      (-> (expect (fn [] (calc/add "1,\n")))
          (.toThrow #"Invalid input"))))))

(test
 "parses delimiter line"
 (fn []
   (-> (expect (calc/add "//,\n1,2"))
       (.toBe 3))))

(test
 "uses alternative delimiter"
 (fn []
   (-> (expect (calc/add "//;\n1;2"))
       (.toBe 3))))

(test
 "allows regex chars as alternative delimiter"
 (fn []
   (-> (expect (calc/add "//[\n1[2"))
       (.toBe 3))))

(test
 "rejects negative numbers"
 (fn []
   (-> (expect #(calc/add "-1"))
       (.toThrow #"Negative numbers not allowed"))))

(test
 "rejects multiple negative numbers with meaningful error message"
 (fn []
   (-> (expect #(calc/add "-1,-2"))
       (.toThrow #"Negative numbers not allowed: -1, -2"))))

(test
 "ignores large numbers"
 (fn []
   (-> (expect (calc/add "2,1001"))
       (.toBe 2))))
