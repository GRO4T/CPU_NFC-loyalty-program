from django.db import models


class Account(models.Model):
    payment_card_serial = models.CharField(max_length=255, primary_key=True)
    points = models.IntegerField()
