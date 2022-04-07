(ns tdd.logic.game-test
  (:require
   ["@jest/globals" :refer [expect test]]
   [tdd.logic.game :as x]))

(test
 "returns 0 turns for empty game"
 (fn []
   (let [game (x/create)]
     (-> (expect (x/turn game))
         (.toBe 0)))))

(test
 "returns 1 turn after 1 move"
 (fn []
   (let [game (-> (x/create)
                  (x/move 0))]
     (-> (expect (x/turn game))
         (.toBe 1)))))

(test
 "returns 2 turns after two moves"
 (fn []
   (let [game (-> (x/create)
                  (x/move 0)
                  (x/move 1))]
     (-> (expect (x/turn game))
         (.toBe 2)))))

(test
 "returns alternating players"
 (fn []
   (let [game (-> (x/create)
                  (x/move 0)
                  (x/move 1))]
     (-> (expect (x/fields game))
         (.toEq ["X" "O" "" "" "" "" "" "" ""])))))

(test
 "throws when trying to play the same field twice"
 (fn []
   (let [game (-> (x/create)
                  (x/move 0))]
     (-> (expect (fn [] (x/move game 0)))
         (.toThrow "Illegal move")))))

(test
 "reports result pending"
 (fn []
   (let [game (-> (x/create))]
     (-> (expect (x/result game))
         (.toEq :pending)))))

(test
 "reports result pending after first move"
 (fn []
   (let [game (-> (x/create)
                  (x/move 0))]
     (-> (expect (x/result game))
         (.toEq :pending)))))

(test
 "reports result X as winner"
 (fn []
   (let [game (-> (x/create ["X" "X" "X" "O" "O" "" "" "" ""]))]
     (-> (expect (x/result game))
         (.toEq :X)))))

(test
 "reports result O as winner"
 (fn []
   (let [game (-> (x/create ["O" "O" "O" "X" "X" "" "" "" ""]))]
     (-> (expect (x/result game))
         (.toEq :O)))))

(test
 "reports result X as winner (col)"
 (fn []
   (let [game (-> (x/create ["" "X" "" "" "X" "O" "O" "X" ""]))]
     (-> (expect (x/result game))
         (.toEq :X)))))

(test
 "reports result X as winner (diag)"
 (fn []
   (let [game (-> (x/create ["X" "" "" "" "X" "O" "O" "" "X"]))]
     (-> (expect (x/result game))
         (.toEq :X)))))

(test
 "reports draw"
 (fn []
   (let [game (-> (x/create ["O" "X" "O" "X" "X" "O" "X" "O" "X"]))]
     (-> (expect (x/result game))
         (.toEq :draw)))))

(test
 "returns winner"
 (fn []
   (let [game (-> (x/create ["X" "" "" "" "X" "O" "O" "" "X"]))]
     (-> (expect (x/winners game))
         (.toEq #{0 4 8})))))
