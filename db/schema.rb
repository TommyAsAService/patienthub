# encoding: UTF-8
# This file is auto-generated from the current state of the database. Instead
# of editing this file, please use the migrations feature of Active Record to
# incrementally modify your database, and then regenerate this schema definition.
#
# Note that this schema.rb definition is the authoritative source for your
# database schema. If you need to create the application database on another
# system, you should be using db:schema:load, not running all the migrations
# from scratch. The latter is a flawed and unsustainable approach (the more migrations
# you'll amass, the slower it'll run and the greater likelihood for issues).
#
# It's strongly recommended that you check this file into your version control system.

ActiveRecord::Schema.define(version: 20151009021155) do

  create_table "dosages", force: :cascade do |t|
    t.integer  "patient_id"
    t.string   "time_taken"
    t.integer  "frequency"
    t.date     "start_date"
    t.datetime "created_at",     null: false
    t.datetime "updated_at",     null: false
    t.string   "treatment_name"
    t.float    "quantity"
    t.string   "unit"
  end

  add_index "dosages", ["patient_id"], name: "index_dosages_on_patient_id"
  add_index "dosages", ["treatment_name"], name: "index_dosages_on_treatment_name"

  create_table "feedbacks", force: :cascade do |t|
    t.integer  "dosage_id"
    t.boolean  "taken"
    t.string   "comment"
    t.datetime "created_at", null: false
    t.datetime "updated_at", null: false
  end

  add_index "feedbacks", ["dosage_id"], name: "index_feedbacks_on_dosage_id"

  create_table "patients", force: :cascade do |t|
    t.string   "name"
    t.string   "address"
    t.string   "contact_number"
    t.string   "patient_number"
    t.datetime "created_at",           null: false
    t.datetime "updated_at",           null: false
    t.string   "token_authentication"
    t.integer  "age"
    t.integer  "user_id"
  end

  add_index "patients", ["user_id"], name: "index_patients_on_user_id"

  create_table "quiz_feedbacks", force: :cascade do |t|
    t.string   "question"
    t.string   "answer"
    t.integer  "patient_id"
    t.datetime "created_at", null: false
    t.datetime "updated_at", null: false
  end

  add_index "quiz_feedbacks", ["patient_id"], name: "index_quiz_feedbacks_on_patient_id"

  create_table "treatments", force: :cascade do |t|
    t.string   "name"
    t.datetime "created_at",     null: false
    t.datetime "updated_at",     null: false
    t.string   "treatment_type"
  end

  create_table "users", force: :cascade do |t|
    t.string   "encrypted_password",     default: "", null: false
    t.string   "reset_password_token"
    t.datetime "reset_password_sent_at"
    t.datetime "remember_created_at"
    t.integer  "sign_in_count",          default: 0,  null: false
    t.datetime "current_sign_in_at"
    t.datetime "last_sign_in_at"
    t.string   "current_sign_in_ip"
    t.string   "last_sign_in_ip"
    t.string   "confirmation_token"
    t.datetime "confirmed_at"
    t.datetime "confirmation_sent_at"
    t.string   "unconfirmed_email"
    t.string   "name"
    t.string   "email"
    t.datetime "created_at"
    t.datetime "updated_at"
    t.string   "contact"
    t.string   "working_address"
  end

  add_index "users", ["email"], name: "index_users_on_email"
  add_index "users", ["reset_password_token"], name: "index_users_on_reset_password_token", unique: true

end
