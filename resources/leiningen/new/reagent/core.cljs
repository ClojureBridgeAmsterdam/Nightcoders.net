(ns {{namespace}}
  (:require [reagent.core :as r]))

(def timer
  (r/atom (js/Date.)))

(def time-color
  (r/atom "red"))

(def time-updater
  (js/setInterval
   #(reset! timer (js/Date.))
   1000))

(defn greeting [message]
  [:h1 message])

(defn clock []
  (let [time-str (-> @timer
                     .toTimeString
                     (clojure.string/split " ")
                     first)]
    [:div.example-clock
     {:style {:color @time-color}}
     time-str]))

(defn color-input []
  [:div.color-input
   "Time color: "
   [:input {:type "text"
            :value @time-color
            :on-change #(reset! time-color (-> % .-target .-value))}]])

(defn simple-example []
  [:div
   (greeting "Hello world, it is now")
   (clock)
   (color-input)])

(r/render-component (simple-example) (.querySelector js/document "#content"))
