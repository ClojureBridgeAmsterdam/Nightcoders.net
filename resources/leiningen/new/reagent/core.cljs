(ns {{namespace}}
  (:require [reagent.core :as r]))

(defonce timer
  (r/atom (js/Date.)))

(defonce time-color
  (r/atom "red"))

(defonce time-updater
  (js/setInterval
   (fn []
     (reset! timer (js/Date.)))
   1000))

(defn greeting [message]
  [:h1 message])

(defn add-hours [date hours]
  (let [current-hours (.getHours date)]
    (doto date
      (.setHours (+ current-hours hours)))))

(defn clock [city time-str]
  [:div.example-clock
   [:div (str city ": ")]
   [:div
    {:style {:color @time-color}}
    time-str]])

(defn color-input []
  [:div.color-input
   "Time color: "
   [:input {:type "text"
            :value @time-color
            :on-change (fn [event]
                         (reset! time-color (-> event .-target .-value)))}]])

(def time-differences
  {"London" -1
   "Abu Dhabi" 3
   "Paris" 0
   "St Petersburg" 2
   "Shanghai" 7
   "Minneapolis" -7
   "Montreal" -6
   "Glasgow" -1})

(defn make-time-str [datetime]
  (-> datetime
      .toTimeString
      (clojure.string/split " ")
      first))

(defn page []
  [:div
   [greeting "Hello world, it is now"]
   (for [[city time-diff] time-differences]
     (let [time-str (-> @timer
                        (add-hours time-diff)
                        make-time-str)]
       [clock city time-str]))
   [color-input]])

(r/render-component [page] (.querySelector js/document "#content"))
