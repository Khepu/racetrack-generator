(ns racetrack-generator.core
  (:require [quil.core :as q]
            [quil.middleware :as m]))

(defn setup
  []
  (q/frame-rate 30)
  (q/color-mode :rgb)
  {})

(defn plot
  [f start end step]
  (map-indexed #(vector %1 (f %2)) (range start end step)))

(defn update-state
  [state]
  {})

(defn draw-state
  [state]
  (q/background 240)
  (q/fill 0 0 0)
  (let [points (plot inc 0 500 20)
        pairs (reduce (fn [lines pair]
                        (if (and (<= 1 (count lines)) (= 2 (count pair)))
                          (let [last-line (last lines)
                                mid-pair [(second last-line) (first pair)]]
                            (conj lines mid-pair (vec pair)))
                          (conj lines (vec pair))))
                      []
                      (partition 2 points))]
    (run! (fn [[[x1 y1] [x2 y2]]] (q/line x1 y1 x2 y2)) pairs)))

(q/defsketch racetrack-generator
  :title "Racetrack Generator"
  :size [500 500]
  :setup setup
  :update update-state
  :draw draw-state
  :features [:keep-on-top]
  :middleware [m/fun-mode])

(defn -main [& args])
