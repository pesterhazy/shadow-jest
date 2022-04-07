(ns tdd.domain.board-test
  (:require
   ["@jest/globals" :refer [expect test]]
   ["@testing-library/react" :as rtl]
   [tdd.domain.board :as sut]
   [uix.core.alpha :as uix]))

(test
 "shows board"
 (fn []
   (rtl/render (uix/as-element [sut/board-do]))
   (-> (expect (rtl/screen.getByTestId "board"))
       (.toBeInTheDocument))))

(test
 "shows move when user clicks field"
 (fn []
   (rtl/render (uix/as-element [sut/board-do]))
   (let [fields (rtl/screen.getAllByTestId "field")
         _ (rtl/fireEvent.click (get fields 0))
         fields (rtl/screen.getAllByTestId "field")]
     (-> (expect (get fields 0)) (.toHaveTextContent "X")))))

(test
 "handles clicking on the same field twice gracefully"
 (fn []
   (rtl/render (uix/as-element [sut/board-do]))
   (let [fields (rtl/screen.getAllByTestId "field")
         _ (rtl/fireEvent.click (get fields 0))
         _ (rtl/fireEvent.click (get fields 0))]
     (-> (expect (get fields 0)) (.toHaveTextContent "X")))))
