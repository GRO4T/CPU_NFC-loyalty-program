from django.http import HttpResponse, HttpRequest, JsonResponse
from django.views.decorators.http import require_GET, require_POST

from .models import Account


@require_GET
def index(request: HttpRequest) -> HttpResponse:
    return HttpResponse("Hello, you're at the NFC Loyalty Program website.")


@require_GET
def get_points(request: HttpRequest, card_id: str) -> JsonResponse:
    account = Account.objects.get(payment_card_serial=card_id)
    return JsonResponse({"points": account.points})


@require_POST
def register_account(request: HttpRequest, card_id: str) -> HttpResponse:
    new_account = Account(payment_card_serial=card_id, points=0)
    new_account.save()
    return HttpResponse()