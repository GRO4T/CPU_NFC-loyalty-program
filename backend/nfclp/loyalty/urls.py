from django.urls import path

from . import views

urlpatterns = [
    path("", views.index, name="index"),
    path("points/<str:card_id>", views.get_points, name="get_points"),
    path("accounts/<str:card_id>", views.register_account, name="register_account")
]
