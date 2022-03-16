(ns tdd.app-spec
  (:require [tdd.app :as x]
            [jest.matchers]))

(js/test "tests run"
         (fn []
           (-> (js/expect 1)
               (.toEqual 1))))
