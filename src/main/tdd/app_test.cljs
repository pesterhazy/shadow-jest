(ns tdd.app-test
  (:require
   ["@jest/globals" :refer [expect test]]
   ["@testing-library/react" :as rtl]
   [tdd.app :as app]
   [uix.core.alpha :as uix]))

(test
 "shows board"
 (fn []
   (rtl/render (uix/as-element [app/app-ui]))
   (-> (expect (rtl/screen.getByTestId "board"))
       (.toBeInTheDocument))))
