# Object Constraint Language (OCL) for Ride-Sharing Application

This document defines OCL constraints for key domain entities in the ride-sharing system.

## User

context User
inv UniqueEmail: User.allInstances()->isUnique(u | u.email)
inv ValidRole: self.role <> null and self.role.toString() in Set{'RIDER', 'DRIVER', 'ADMIN'}
inv PasswordLength: self.password.size() >= 6
inv RequiredNames: self.firstName.size() > 0 and self.lastName.size() > 0

## Ride

context Ride
inv ValidRider: self.riderId <> null
inv ValidStatus: self.status <> null and self.status.toString() in Set{'REQUESTED', 'ACCEPTED', 'IN_PROGRESS', 'COMPLETED', 'CANCELLED'}
inv ValidFare: self.fare >= 0.0
inv PickupDropoffNotEmpty: self.pickupLocation.size() > 0 and self.dropoffLocation.size() > 0

## Payment

context Payment
inv ValidAmount: self.amount > 0.0
inv ValidStatus: self.status <> null and self.status.size() > 0
inv RideReference: self.rideId <> null

## Notification

context Notification
inv ValidRecipient: self.userId <> null
inv MessageNotEmpty: self.message.size() > 0
inv TypeNotEmpty: self.notificationType.size() > 0
