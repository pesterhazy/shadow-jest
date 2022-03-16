(ns tdd.app-test
  (:require [tdd.app :as x]
            [jest.matchers]))

(js/test "handles empty string"
         (fn []
           (-> (js/expect (x/rot13 ""))
               (.toEqual ""))))

(js/test "handles single letter"
         (fn []
           (-> (js/expect (x/rot13 "a"))
               (.toEqual "n"))))

(js/test "handles uppercase letter"
         (fn []
           (-> (js/expect (x/rot13 "A"))
               (.toEqual "N"))))

(js/test "handles numbers"
         (fn []
           (-> (js/expect (x/rot13 "1"))
               (.toEqual "1"))))

(js/test "handles letter after n"
         (fn []
           (-> (js/expect (x/rot13 "o"))
               (.toEqual "b"))))

(js/test "handles multiple letter"
         (fn []
           (-> (js/expect (x/rot13 "Hello n1ce World"))
               (.toEqual "Uryyb a1pr Jbeyq"))))
