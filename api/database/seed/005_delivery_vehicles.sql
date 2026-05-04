-- 005_delivery_vehicles.sql
-- Seed data for delivery_vehicles table
-- Provides realistic test data for development and demo purposes

INSERT INTO delivery_vehicles (vehicle_id, branch_id, vehicle_type, capacity, status) VALUES
  (1, 1, 'truck', 5000.0, 'available'),
  (2, 1, 'van', 1500.0, 'in-transit'),
  (3, 2, 'truck', 8000.0, 'available'),
  (4, 2, 'motorcycle', 100.0, 'maintenance'),
  (5, 3, 'van', 2000.0, 'available');
