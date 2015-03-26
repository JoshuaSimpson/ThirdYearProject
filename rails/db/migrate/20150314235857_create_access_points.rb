class CreateAccessPoints < ActiveRecord::Migration
  def change
    create_table :access_points do |t|
      t.string :mac
      t.string :location
      t.datetime :time
      t.string :notes

      t.timestamps
    end
  end
end
