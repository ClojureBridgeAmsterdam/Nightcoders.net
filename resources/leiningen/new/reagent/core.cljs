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

(defn add-hours
  "Adds hours (number) to date."
  [date hours]
  (let [current-hours (.getHours date)]
    (doto date
      (.setHours (+ current-hours hours)))))

(defn color-input []
  [:div.color-input
   "Time color: "
   [:input {:type "text"
            :value @time-color
            :on-change (fn [event]
                         (reset! time-color (-> event .-target .-value)))}]])

(def base-url
  "../../../")

(def city-data
  {"Amsterdam"     {:time-diff  0 :flag-url (str base-url "/flags/netherlands.png")}
   "Paris"         {:time-diff  0 :flag-url (str base-url "/flags/france.png")}
   "London"        {:time-diff -1 :flag-url (str base-url "/flags/united-kingdom.png")}
   "Abu Dhabi"     {:time-diff  3 :flag-url (str base-url "/flags/united-arab-emirates.png")}
   "St Petersburg" {:time-diff  2 :flag-url (str base-url "/flags/russia.png")}
   "Shanghai"      {:time-diff  7 :flag-url (str base-url "/flags/china.png")}
   "Minneapolis"   {:time-diff -7 :flag-url (str base-url "/flags/united-states-of-america.png")}
   "Montreal"      {:time-diff -6 :flag-url (str base-url "/flags/canada.png")}
   "Rome"          {:time-diff  0 :flag-url (str base-url "/flags/italy.png")}})

(defn make-time-str [datetime]
  (-> datetime
      .toTimeString
      (clojure.string/split " ")
      first))

(defn flag-img [url]
  [:img {:src url}])

(defn clock [city time-str flag-url]
  [:div.clock
   [:div (str city ": ")]
   ;; Uncomment this line to add the flags
   ; [:div [:img {:src flag-url}]]
   [:div
    {:style {:color @time-color}}
    time-str]])

;; Use for in function below to .........
(defn for-example-div []
  [:div
   (doall
    (for [x (range 10)]
      [:div x]))])

(defn questions-link []
  [:a.questions-link {:href (str base-url "/questions.html")
                      :target "_blank"}
   "Click here to go to the questions"])

(defn page []
  [:div
   [greeting "Hello world, it is now"]
   (doall
    (for [[city {:keys [time-diff flag-url]}] city-data]
      (let [time-str (-> @timer
                         ;; here convert all time-diff to +1
                         (add-hours time-diff)
                         make-time-str)]
        ^{:key city}
        [clock city time-str flag-url])))
   [color-input]
   [questions-link]])

(r/render-component [page] (.querySelector js/document "#content"))
