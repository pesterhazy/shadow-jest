(ns todomvc.app-spec
  (:require [todomvc.app :as x]
            [jest.matchers]))

(js/test "fail"
         (fn []
           (-> (js/expect 1)
               (.toEqual 1))))
