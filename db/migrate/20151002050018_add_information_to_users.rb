class AddInformationToUsers < ActiveRecord::Migration
  def change
    add_column :users, :contact, :string
    add_column :users, :working_address, :string
  end
end
