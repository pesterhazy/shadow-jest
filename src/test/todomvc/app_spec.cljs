(ns todomvc.app-spec
  (:require ["@testing-library/react" :as rtl]
            ["@testing-library/jest-dom/extend-expect" :as for-side-effects]
            [uix.core.alpha :as uix]
            [todomvc.app :as x]))

(js/test "shows string"
         (fn []
           (rtl/render (uix/as-element [x/app]))
           (-> (js/expect (rtl/screen.getByRole "heading"))
               (.toHaveTextContent "xxx"))))
