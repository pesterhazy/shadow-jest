(ns tdd.game-test
  (:require
   ["@jest/globals" :refer [expect test]]
   [tdd.game :as x]))

(test
 "returns 0 moves for empty game"
 (fn []
   (let [game (x/create)]
     (-> (expect (x/turn game))
         (.toBe 0)))))

(test
 "returns 1 move after 1 move"
 (fn []
   (let [game (-> (x/create)
                  (x/move 0))]
     (-> (expect (x/turn game))
         (.toBe 1)))))

(test
 "returns 2 moves after two moves"
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
