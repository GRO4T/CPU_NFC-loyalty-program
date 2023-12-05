#!/bin/bash
(
    cd ./nfclp || exit

    python manage.py collectstatic

    python manage.py migrate

    python manage.py ensure_superuser --no-input \
        --username "$DJANGO_SUPERUSER_USERNAME" \
        --email "$DJANGO_SUPERUSER_EMAIL" \
        --password "$DJANGO_SUPERUSER_PASSWORD"
)