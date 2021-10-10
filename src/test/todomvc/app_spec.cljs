(ns todomvc.app-spec
  (:require ["@testing-library/react" :as rtl]
            [uix.core.alpha :as uix]
            [todomvc.app :as x]))

(js/test "initial screen"
         (fn []
           (rtl/render (uix/as-element [x/app]))
           (-> (js/expect (rtl/screen.getByRole "heading"))
               (.toHaveTextContent "todos"))
           (-> (js/expect (rtl/screen.getByPlaceholderText "What needs to be done?"))
               (.toBeInTheDocument))
           (-> (js/expect (rtl/screen.getByText "Start REPL"))
               (.toBeInTheDocument))
           (-> (js/expect (rtl/screen.getByText "1 item left"))
               (.toBeInTheDocument))
           (-> (js/expect (rtl/screen.getByText "All"))
               (.toHaveClass "selected"))
           (-> (js/expect (rtl/screen.getByText "Active"))
               (.-not)
               (.toHaveClass "selected"))
           (-> (js/expect (rtl/screen.getByText "Completed"))
               (.-not)
               (.toHaveClass "selected"))))

(js/test "add todo"
         (fn []
           (rtl/render (uix/as-element [x/app]))
           (rtl/fireEvent.change (rtl/screen.getByRole "textbox")
                                 #js{:target #js{:value "Write macros"}})
           (rtl/fireEvent.keyDown (rtl/screen.getByRole "textbox")
                                  #js{:key "Enter" :code 13 :charCode 13})
           (-> (js/expect (rtl/screen.getByText "Write macros"))
               (.toBeInTheDocument))
           (-> (js/expect (-> (rtl/screen.getByRole "textbox")
                              .-value))
               (.toBe ""))
           (-> (js/expect (rtl/screen.getByText "2 items left"))
               (.toBeInTheDocument))))

;; FIXME: custom matcher for CLJS data structures?

(defn explain-not= [a b]
  (if (= a b)
    nil
    (str "Not the same: " (pr-str [a b]))))

(js/test "can't create empty todo"
         (fn []
           (rtl/render (uix/as-element [x/app]))
           (rtl/fireEvent.keyDown (rtl/screen.getByRole "textbox")
                                  #js{:key "Enter" :code 13 :charCode 13})
           ;; custom matcher?
           (-> (js/expect (explain-not= (map #(.-textContent %) (rtl/screen.getAllByTestId #"item"))
                                        ["Start REPL"]))
               (.toBeNull))))

(js/test "remove todo"
         (fn []
           (rtl/render (uix/as-element [x/app]))
           (rtl/fireEvent.click (rtl/screen.getByTestId "destroy"))
           (-> (js/expect (rtl/screen.queryByText "Start REPL"))
               .toBeNull)
           (-> (js/expect (rtl/screen.queryByTestId "footer"))
               .toBeNull)))

(js/test "complete todo"
         (fn []
           (rtl/render (uix/as-element [x/app]))
           (rtl/fireEvent.click (rtl/screen.getByTestId "toggle-0"))
           (-> (js/expect (-> (rtl/screen.getByText "Start REPL")
                              (.closest "li")))
               (.toHaveClass "completed"))
           (-> (js/expect (rtl/screen.getByText "0 items left"))
               (.toBeInTheDocument))))
