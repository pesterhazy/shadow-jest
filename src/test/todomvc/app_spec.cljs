(ns todomvc.app-spec
  (:require [todomvc.app :as x]
            [jest.matchers]))

(js/test "tests run"
         (fn []
           (-> (js/expect 1)
               (.toEqual 1))))
