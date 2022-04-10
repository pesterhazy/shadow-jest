(ns tdd.dom)

(defn create-div [text]
  (let [el (js/document.createElement "div")]
    (set! (.-textContent el) text)
    (.appendChild js/document.body el)))
