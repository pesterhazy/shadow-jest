(ns tdd.dom-test
  (:require ["@jest/globals" :refer [test expect]]
            [tdd.dom :as dom]))

(test
 "tbd"
 (fn []
   (dom/create-div "my text param")
   (let [node (-> js/document.body .-firstChild)]
     (-> (expect (-> node .-textContent)) (.toBe "my text param")))))
