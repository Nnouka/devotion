# Devotion For the Churches of Christ

* A minister creates a congregation

* A minister adds user to congregations

* A user can create a devotional and schedule it

* A user can check a pulse from a devotional

* A user can comment on a pulse from a devotional

* A user can indicate if a pulse was relevant or not


A devotional has a duration. If a devotional has a duration of n days, then it must have n pulses.
A pulse has a title, an exhortation, some scripture references, some prayer points.
Scripture reference is book, chapter and verse
Prayer point is, an issue and God’s answer

# Domain Model
![Devotion_Domain_Model](./images/domain_models.png)

## Creating a congregation

1. Select a suitable name for a congregation

endpoint: `api/protected/congregations/create`

expected request
```json
{
  "name": "Friendly name"
}
```

expected response
```json
{
  "id": 1,
  "name": "Friendly name",
  "displayPic": null,
  "fullAddress": null
}
```
