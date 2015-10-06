class RemoveThingOutOfUsers < ActiveRecord::Migration
  def change
    remove_column :users, :uid
    remove_column :users, :nickname
    remove_column :users, :image
    remove_column :users, :tokens
    remove_column :users, :provider
  end
end
