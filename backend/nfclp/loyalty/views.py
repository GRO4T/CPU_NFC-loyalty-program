from django.http import HttpResponse, HttpRequest, JsonResponse


def index(request: HttpRequest) -> HttpResponse:
    return HttpResponse("Hello, you're at the NFC Loyalty Program website.")


def get_points(request: HttpRequest, card_id: str) -> JsonResponse:
    return JsonResponse({"points": 500})