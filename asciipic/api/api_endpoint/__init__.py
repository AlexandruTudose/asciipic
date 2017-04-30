"""API  endpoint for the AsciiPic API."""

from asciipic.api import base as base_api
from asciipic.api.api_endpoint import version
from asciipic.api.api_endpoint import user


class APIEndpoint(base_api.BaseAPI):

    """This API will have all the API endpoints."""

    # A list that contains all the resources (endpoints) available for the
    # current metadata service
    resources = [
        ("version", version.Version),
        ("user", user.UserEndpoint)
    ]

    # Whether this application should be available for clients
    exposed = True
