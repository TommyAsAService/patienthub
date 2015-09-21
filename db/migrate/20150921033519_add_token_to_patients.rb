class AddTokenToPatients < ActiveRecord::Migration
  def change
    add_column :patients, :token_authentication, :string
    remove_column :patients, :ages
    add_column :patients, :age, :string
  end
end
