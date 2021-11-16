package io.github.minthurein.carrier.portfolio.way.api;

import io.github.minthurein.carrier.infrastructure.jwt.service.UserJWTPayload;
import io.github.minthurein.carrier.portfolio.user.domain.UserName;
import io.github.minthurein.carrier.portfolio.way.Data.MultipleWayModel;
import io.github.minthurein.carrier.portfolio.way.Data.WayModel;
import io.github.minthurein.carrier.portfolio.way.Data.WayPostRequestDTO;
import io.github.minthurein.carrier.portfolio.way.Data.WayPutRequestDTO;
import io.github.minthurein.carrier.portfolio.way.service.WayService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.ResponseEntity.of;

@RestController
class WayRestController {

    private final WayService wayService;

    WayRestController(WayService wayService) {
        this.wayService = wayService;
    }

    @PostMapping("/ways")
    public WayModel postWay(@AuthenticationPrincipal UserJWTPayload jwtPayload,
                                @Valid @RequestBody WayPostRequestDTO dto) {
        var wayCreated = wayService.createNewWay(jwtPayload.getUserId(), dto.towayContents());
        return WayModel.fromWay(wayCreated);
    }

    @GetMapping("/ways")
    public MultipleWayModel getWays(Pageable pageable) {
        final var way = wayService.getWays(pageable);
        return MultipleWayModel.fromWays(way);
    }

    @GetMapping(value = "/ways", params = {"owner"})
    public MultipleWayModel getWaysByOwner(@RequestParam String owner, Pageable pageable) {
        final var ways = wayService.getWayByOwnerName(owner, pageable);
        return MultipleWayModel.fromWays(ways);
    }

    @GetMapping(value = "/ways", params = {"tag"})
    public MultipleWayModel getWaysByTag(@RequestParam String tag, Pageable pageable) {
        final var ways = wayService.getWaysByTag(tag, pageable);
        return MultipleWayModel.fromWays(ways);
    }

    @GetMapping(value = "/ways", params = {"requestedUser"})
    public MultipleWayModel getWayByRequestUsername(@RequestParam UserName requestedUser, Pageable pageable) {
        final var ways = wayService.getWayRequestedByUsername(requestedUser, pageable);
        return MultipleWayModel.fromWays(ways);
    }

    @GetMapping("/ways/feed")
    public MultipleWayModel getFeed(@AuthenticationPrincipal UserJWTPayload jwtPayload, Pageable pageable) {
        final var ways = wayService.getFeedByUserId(jwtPayload.getUserId(), pageable);
        return MultipleWayModel.fromWays(ways);
    }

    @GetMapping("/ways/{slug}")
    public ResponseEntity<WayModel> getWayBySlug(@PathVariable String slug) {
        return of(wayService.getWayBySlug(slug)
                .map(WayModel::fromWay));
    }

    @PutMapping("/ways/{slug}")
    public WayModel putWayBySlug(@AuthenticationPrincipal UserJWTPayload jwtPayload,
                                         @PathVariable String slug,
                                         @RequestBody WayPutRequestDTO dto) {
        final var wayUpdated = wayService.updateWay(jwtPayload.getUserId(), slug, dto.toUpdateRequest());
        return WayModel.fromWay(wayUpdated);
    }

    @PostMapping("/ways/{slug}/request")
    public WayModel reuquestWayBySlug(@AuthenticationPrincipal UserJWTPayload jwtPayload,
                                              @PathVariable String slug) {
        var wayRequested = wayService.requestWay(jwtPayload.getUserId(), slug);
        return WayModel.fromWay(wayRequested);
    }

    @DeleteMapping("/ways/{slug}/request")
    public WayModel unRequestWayBySlug(@AuthenticationPrincipal UserJWTPayload jwtPayload,
                                                @PathVariable String slug) {
        var wayUnrequest = wayService.unrequestWay(jwtPayload.getUserId(), slug);
        return WayModel.fromWay(wayUnrequest);
    }

    @ResponseStatus(NO_CONTENT)
    @DeleteMapping("/ways/{slug}")
    public void deleteWayBySlug(@AuthenticationPrincipal UserJWTPayload jwtPayload,
                                    @PathVariable String slug) {
        wayService.deleteWayBySlug(jwtPayload.getUserId(), slug);
    }
}
