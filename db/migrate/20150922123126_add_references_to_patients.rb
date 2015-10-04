class AddReferencesToPatients < ActiveRecord::Migration
  def change
    add_reference :patients, :user, index: true
    add_foreign_key :patients, :users
  end
end
