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
           (-> (js/expect (rtl/screen.getByText "Create REPL"))
               (.toBeInTheDocument))
           (-> (js/expect (rtl/screen.getByText "1 item left"))
               (.toBeInTheDocument))
           (-> (js/expect (rtl/screen.getByText "All"))
               (.toHaveClass "selected"))))
