(ns tdd.dom-test
  (:require ["@jest/globals" :refer [test expect]]
            [tdd.dom :as dom]))

(test
 "tbd"
 (fn []
   (dom/create)
   (-> (expect) .pass)))
