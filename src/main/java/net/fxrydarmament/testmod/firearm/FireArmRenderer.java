package net.fxrydarmament.testmod.firearm;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.fxrydarmament.testmod.client.render.RenderPass;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.resources.ResourceLocation;

import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import software.bernie.geckolib.cache.GeckoLibCache;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.cache.object.GeoCube;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.cache.object.GeoVertex;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class FireArmRenderer
        extends GeoItemRenderer<FireArmItem> {

    // Arms Model - Wide (Classic/Steve)
    private static final ResourceLocation RIGHT_ARM_MODEL_WIDE =
            ResourceLocation.fromNamespaceAndPath("fxrydarmament", "geo/firearm/right_arm.geo.json");
    private static final ResourceLocation LEFT_ARM_MODEL_WIDE =
            ResourceLocation.fromNamespaceAndPath("fxrydarmament", "geo/firearm/left_arm.geo.json");

    // Arms Model - Slim (Alex)
    private static final ResourceLocation RIGHT_ARM_MODEL_SLIM =
            ResourceLocation.fromNamespaceAndPath("fxrydarmament", "geo/firearm/right_arm_slim.geo.json");
    private static final ResourceLocation LEFT_ARM_MODEL_SLIM =
            ResourceLocation.fromNamespaceAndPath("fxrydarmament", "geo/firearm/left_arm_slim.geo.json");

    private ResourceLocation resolveArmModel(ResourceLocation wideModel, ResourceLocation slimModel) {
        var player = net.minecraft.client.Minecraft.getInstance().player;

        if (player == null) {
            return wideModel; // fallback aman kalau player null
        }

        boolean isSlimArm = player.getSkin().model() == net.minecraft.client.resources.PlayerSkin.Model.SLIM;
        return isSlimArm ? slimModel : wideModel;
    }

    // define Bone Name
    private static final String RIGHT_ARM_BONE = "rightarm";
    private static final String LEFT_ARM_BONE = "leftarm";

    // >>> TARUH DI SINI, level class, BUKAN di dalam method lain <
    private ItemDisplayContext currentTransformType = ItemDisplayContext.NONE;

    public ItemDisplayContext getCurrentTransformType() {
        return currentTransformType;
    }

    @Override
    public void renderByItem(
            ItemStack stack,
            ItemDisplayContext transformType,
            PoseStack poseStack,
            MultiBufferSource bufferSource,
            int packedLight,
            int packedOverlay
    ) {
        this.currentTransformType = transformType;
        super.renderByItem(stack, transformType, poseStack, bufferSource, packedLight, packedOverlay);
    }
    // >>> SAMPAI SINI <

    // Constructor
    public FireArmRenderer() {
        super(new FireArmModel());

        addRenderLayer(
                new HandsLayer<>(this)
        );
    }


    // Bone Rendering
    @Override
    public void renderCubesOfBone(
            PoseStack poseStack,
            GeoBone bone,
            VertexConsumer buffer,
            int packedLight,
            int packedOverlay,
            int color
    ) {

        boolean isRightArmBone = RIGHT_ARM_BONE.equals(bone.getName());
        boolean isLeftArmBone = LEFT_ARM_BONE.equals(bone.getName());


        // Bone leftarm/rightarm cuma jadi reference point,
        // mesh aslinya TIDAK PERNAH dirender langsung
        if (isRightArmBone || isLeftArmBone) {

            // Cuma render hand model pengganti pas hands pass
            if (RenderPass.isHands()) {

                if (isRightArmBone) {
                    renderRightArm(
                            poseStack,
                            bone,
                            buffer,
                            packedLight,
                            packedOverlay,
                            color
                    );
                } else {
                    renderLeftArm(
                            poseStack,
                            bone,
                            buffer,
                            packedLight,
                            packedOverlay,
                            color
                    );
                }
            }

            return; // skip mesh asli, baik hands pass maupun main pass
        }

        // Hand Pass - bone selain leftarm/rightarm di-skip total
        if (RenderPass.isHands()) {
            return;
        }

        super.renderCubesOfBone(
                poseStack,
                bone,
                buffer,
                packedLight,
                packedOverlay,
                color
        );
    }
    private void renderRightArm(
            PoseStack poseStack,
            GeoBone referenceBone,
            VertexConsumer buffer,
            int packedLight,
            int packedOverlay,
            int color
    ) {

        BakedGeoModel armModel =
                GeckoLibCache.getBakedModels()
                        .get(resolveArmModel(RIGHT_ARM_MODEL_WIDE, RIGHT_ARM_MODEL_SLIM));

        if (armModel == null) {
            return;
        }

        GeoBone armBone =
                armModel.getBone("rightarm")
                        .orElse(null);

        if (armBone == null) {
            return;
        }

        poseStack.pushPose();


        applyArmReferenceTransform(
                poseStack,
                referenceBone,
                armBone
        );


        super.renderCubesOfBone(
                poseStack,
                armBone,
                buffer,
                packedLight,
                packedOverlay,
                color
        );

        poseStack.popPose();
    }


    private void renderLeftArm(
            PoseStack poseStack,
            GeoBone referenceBone,
            VertexConsumer buffer,
            int packedLight,
            int packedOverlay,
            int color
    ) {

        BakedGeoModel armModel =
                GeckoLibCache.getBakedModels()
                        .get(resolveArmModel(LEFT_ARM_MODEL_WIDE, LEFT_ARM_MODEL_SLIM));

        if (armModel == null) {
            return;
        }

        GeoBone armBone =
                armModel.getBone("leftarm")
                        .orElse(null);

        if (armBone == null) {
            return;
        }

        poseStack.pushPose();

        applyArmReferenceTransform(
                poseStack,
                referenceBone,
                armBone
        );

        super.renderCubesOfBone(
                poseStack,
                armBone,
                buffer,
                packedLight,
                packedOverlay,
                color
        );

        poseStack.popPose();
    }


    private void applyArmReferenceTransform(
            PoseStack poseStack,
            GeoBone referenceBone,
            GeoBone armBone
    ) {

        if (referenceBone.getCubes().isEmpty()) {
            return;
        }

        if (armBone.getCubes().isEmpty()) {
            return;
        }

        GeoCube referenceCube =
                referenceBone.getCubes().get(0);

        GeoCube armCube =
                armBone.getCubes().get(0);

        GeoVertex referenceVertex =
                referenceCube.quads()[0].vertices()[0];

        GeoVertex armVertex =
                armCube.quads()[0].vertices()[0];

        double dx =
                referenceVertex.position().x()
                        - armVertex.position().x();

        double dy =
                referenceVertex.position().y()
                        - armVertex.position().y();

        double dz =
                referenceVertex.position().z()
                        - armVertex.position().z();

        poseStack.translate(
                dx,
                dy,
                dz
        );
    }
}