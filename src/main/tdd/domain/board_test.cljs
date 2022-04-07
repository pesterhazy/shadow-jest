(ns tdd.domain.board-test
  (:require
   ["@jest/globals" :refer [expect test]]
   ["@testing-library/react" :as rtl]
   [tdd.domain.board :as sut]
   [tdd.logic.game :as game]
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

(test
 "shows message that player X won"
 (fn []
   (rtl/render (uix/as-element [sut/board-do {:game (game/create ["X" "X" "X"
                                                                  "O" "O" ""
                                                                  "" "" ""])}]))
   (-> (expect (rtl/screen.getByText "Player X won"))
       (.toBeInTheDocument))))

(test
 "shows message that player O won"
 (fn []
   (rtl/render (uix/as-element [sut/board-do {:game (game/create ["O" "O" "O"
                                                                  "X" "X" ""
                                                                  "" "" ""])}]))
   (-> (expect (rtl/screen.getByText "Player O won"))
       (.toBeInTheDocument))))

(test
 "shows message that game ended in draw"
 (fn []
   (rtl/render (uix/as-element [sut/board-do {:game (game/create ["O" "X" "O"
                                                                  "X" "X" "O"
                                                                  "X" "O" "X"])}]))
   (-> (expect (rtl/screen.getByText "Draw"))
       (.toBeInTheDocument))))

(test
 "game frozen when player won"
 (fn []
   (rtl/render (uix/as-element [sut/board-do {:game (game/create ["O" "O" "O"
                                                                  "X" "X" ""
                                                                  "" "" ""])}]))
   (let [fields (rtl/screen.getAllByTestId "field")
         _ (rtl/fireEvent.click (get fields 5))]
     (-> (expect (get fields 5)) (.toHaveTextContent "")))))
