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

ActiveRecord::Schema.define(version: 20150922113648) do

  create_table "medications", force: :cascade do |t|
    t.string   "name"
    t.string   "description"
    t.string   "label"
    t.datetime "created_at",  null: false
    t.datetime "updated_at",  null: false
  end

  create_table "patient_medications", force: :cascade do |t|
    t.string   "label"
    t.datetime "start_time"
    t.integer  "patient_id"
    t.string   "notes"
    t.integer  "time_take_per_day"
    t.integer  "amount_given"
    t.datetime "created_at",        null: false
    t.datetime "updated_at",        null: false
    t.string   "medication_name"
  end

  add_index "patient_medications", ["medication_name"], name: "index_patient_medications_on_medication_name"
  add_index "patient_medications", ["patient_id"], name: "index_patient_medications_on_patient_id"

  create_table "patients", force: :cascade do |t|
    t.string   "name"
    t.string   "address"
    t.string   "contact_number"
    t.string   "patient_number"
    t.datetime "created_at",           null: false
    t.datetime "updated_at",           null: false
    t.string   "token_authentication"
    t.string   "age"
  end

  create_table "users", force: :cascade do |t|
    t.string   "uid",                    default: "", null: false
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
    t.string   "nickname"
    t.string   "image"
    t.string   "email"
    t.text     "tokens"
    t.datetime "created_at"
    t.datetime "updated_at"
    t.string   "provider",                            null: false
  end

  add_index "users", ["email"], name: "index_users_on_email"
  add_index "users", ["reset_password_token"], name: "index_users_on_reset_password_token", unique: true
  add_index "users", ["uid"], name: "index_users_on_uid_and_provider", unique: true

end
