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
