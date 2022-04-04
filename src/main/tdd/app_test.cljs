(ns tdd.app-test
  (:require ["@jest/globals" :refer [test expect]]
            ["@testing-library/react" :as rtl]
            [uix.core.alpha :as uix]
            [tdd.app]))

(test
 "shows start button"
 (fn []
   (rtl/render (uix/as-element [tdd.app/app-ui]))
   (-> (expect (rtl/screen.getByText "Start"))
       (.toBeInTheDocument))))

(test
 "shows board"
 (fn []
   (rtl/render (uix/as-element [tdd.app/board-ui]))
   (-> (expect (-> (rtl/screen.getAllByTestId "field")
                   count))
       (.toBe 9))))
